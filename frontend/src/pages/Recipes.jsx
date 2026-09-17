import { useEffect, useState } from 'react'
import { api } from '../lib/api.js'

/** UseCase1001 (create a recipe) and UseCase1004 (share it as a post). */
export default function Recipes() {
  const [mine, setMine] = useState([])
  const [catalog, setCatalog] = useState([])
  const [restrictions, setRestrictions] = useState([])
  const [detail, setDetail] = useState(null)
  const [error, setError] = useState(null)
  const [form, setForm] = useState({ name: '', guide: '', servings: 4, restrictionIds: [] })
  const [lines, setLines] = useState([{ ingredientName: '', quantity: '' }])

  const load = () => api.get('/recipes/mine').then(setMine).catch(e => setError(e.message))

  useEffect(() => {
    load()
    api.get('/catalog/ingredients').then(setCatalog).catch(() => {})
    api.get('/catalog/restrictions').then(setRestrictions).catch(() => {})
  }, [])

  const setLine = (i, key) => (e) => {
    const copy = [...lines]
    copy[i] = { ...copy[i], [key]: e.target.value }
    setLines(copy)
  }

  const create = async () => {
    setError(null)
    try {
      const body = {
        name: form.name,
        guide: form.guide,
        servings: Number(form.servings),
        ingredients: lines
          .filter(l => l.ingredientName && l.quantity)
          .map(l => ({ ingredientName: l.ingredientName, quantity: Number(l.quantity) })),
        restrictionIds: form.restrictionIds.map(Number)
      }
      const created = await api.post('/recipes', body)
      setDetail(created)
      setForm({ name: '', guide: '', servings: 4, restrictionIds: [] })
      setLines([{ ingredientName: '', quantity: '' }])
      load()
    } catch (e) { setError(e.message) }
  }

  const share = async (id) => {
    setError(null)
    try {
      await api.post('/posts', { recipeId: id, isPrivate: false, isFavourite: false, status: 'published' })
      alert('Shared to the feed')
    } catch (e) { setError(e.message) }
  }

  return (
    <div>
      {error && <div className="error">{error}</div>}

      <div className="card">
        <h3>New recipe</h3>
        <p className="muted">
          Energy is not entered: the server sums it from the ingredients and divides by servings.
        </p>
        <div className="stack" style={{ maxWidth: 560 }}>
          <input placeholder="name" value={form.name}
                 onChange={e => setForm({ ...form, name: e.target.value })} />
          <textarea placeholder="preparation steps" rows={3} value={form.guide}
                    onChange={e => setForm({ ...form, guide: e.target.value })} />
          <input type="number" min="1" placeholder="servings" value={form.servings}
                 onChange={e => setForm({ ...form, servings: e.target.value })} />

          {lines.map((l, i) => (
            <div className="row" key={i}>
              <select value={l.ingredientName} onChange={setLine(i, 'ingredientName')} style={{ flex: 1 }}>
                <option value="">— ingredient —</option>
                {catalog.map(c => <option key={c.name} value={c.name}>{c.name} ({c.kcal} kcal)</option>)}
              </select>
              <input type="number" placeholder="grams" style={{ width: 110 }}
                     value={l.quantity} onChange={setLine(i, 'quantity')} />
            </div>
          ))}
          <button className="small" onClick={() => setLines([...lines, { ingredientName: '', quantity: '' }])}>
            + another ingredient
          </button>

          <select multiple size={4} value={form.restrictionIds}
                  onChange={e => setForm({
                    ...form,
                    restrictionIds: Array.from(e.target.selectedOptions, o => o.value)
                  })}>
            {restrictions.map(r => <option key={r.id} value={r.id}>{r.type} — {r.description}</option>)}
          </select>

          <button className="primary" onClick={create}>Create recipe</button>
        </div>
      </div>

      {detail && (
        <div className="card">
          <h3>{detail.name}</h3>
          <div className="muted">
            total <span className="kcal">{detail.kcalSum} kcal</span> ·
            {' '}{String(detail.servings)} servings ·
            {' '}<span className="kcal">{detail.kcalPerServing} kcal / portion</span>
          </div>
          <table>
            <thead><tr><th>ingredient</th><th>grams</th><th>kcal/100g</th><th>contributes</th></tr></thead>
            <tbody>
              {detail.ingredients.map(i => (
                <tr key={i.ingredientName}>
                  <td>{i.ingredientName}</td><td>{i.quantity}</td>
                  <td>{i.kcalPer100g}</td><td>{i.kcalContributed}</td>
                </tr>
              ))}
            </tbody>
          </table>
          {detail.restrictions.length > 0 &&
            <p className="muted">tags: {detail.restrictions.join(', ')}</p>}
        </div>
      )}

      <div className="card">
        <h3>My recipes</h3>
        <table>
          <tbody>
            {mine.map(r => (
              <tr key={r.id}>
                <td>{r.name}</td>
                <td className="kcal">{r.kcalPerServing} kcal / portion</td>
                <td style={{ width: 1 }}>
                  <button className="small" onClick={() => api.get(`/recipes/${r.id}`).then(setDetail)}>
                    view
                  </button>
                </td>
                <td style={{ width: 1 }}>
                  <button className="small" onClick={() => share(r.id)}>share</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        {mine.length === 0 && <p className="muted">No recipes yet.</p>}
      </div>
    </div>
  )
}
