import { useEffect, useState } from 'react'
import { api } from '../lib/api.js'

export default function Feed() {
  const [posts, setPosts] = useState([])
  const [openId, setOpenId] = useState(null)
  const [comments, setComments] = useState([])
  const [draft, setDraft] = useState('')
  const [error, setError] = useState(null)

  const load = () => api.get('/posts/feed').then(setPosts).catch(e => setError(e.message))
  useEffect(() => { load() }, [])

  const open = async (id) => {
    setOpenId(id)
    setComments(await api.get(`/posts/${id}/comments`))
  }

  const send = async () => {
    if (!draft.trim()) return
    try {
      await api.post(`/posts/${openId}/comments`, { description: draft })
      setDraft('')
      setComments(await api.get(`/posts/${openId}/comments`))
      load()
    } catch (e) { setError(e.message) }
  }

  return (
    <div>
      {error && <div className="error">{error}</div>}
      {posts.length === 0 && <p className="muted">Nothing published yet.</p>}

      {posts.map(p => (
        <div className="card" key={p.id}>
          <div className="row">
            <h3 style={{ flex: 1 }}>{p.recipeName || '(photo post)'}</h3>
            <span className="kcal">{p.kcalPerServing} kcal / portion</span>
          </div>
          <div className="muted">
            by {p.authorUsername} · {new Date(p.createdAt).toLocaleString()} · {p.commentCount} comments
            {p.isFavourite && ' · favourite'}
          </div>
          <div className="row" style={{ marginTop: 8 }}>
            <button className="small" onClick={() => open(p.id)}>
              {openId === p.id ? 'comments below' : 'open comments'}
            </button>
          </div>

          {openId === p.id && (
            <div style={{ marginTop: 10, borderTop: '1px solid var(--line)', paddingTop: 10 }}>
              {comments.map(c => (
                <div key={c.id} style={{ marginBottom: 6 }}>
                  <b>{c.authorUsername}</b> <span className="muted">
                    {new Date(c.createdAt).toLocaleString()}</span>
                  <div>{c.description}</div>
                </div>
              ))}
              <div className="row" style={{ marginTop: 8 }}>
                <input style={{ flex: 1 }} placeholder="write a comment"
                       value={draft} onChange={e => setDraft(e.target.value)} />
                <button className="primary" onClick={send}>Send</button>
              </div>
            </div>
          )}
        </div>
      ))}
    </div>
  )
}
