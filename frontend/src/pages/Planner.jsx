import { useEffect, useState } from 'react'
import { api } from '../lib/api.js'

/** UseCase1002 - log a meal and see the daily totals. */
export default function Planner() {
  const [entries, setEntries] = useState([])
  const [feed, setFeed] = useState([])
  const [daily, setDaily] = useState([])
  const [macros, setMacros] = useState([])
  const [error, setError] = useState(null)
  const [pick, setPick] = useState([])
  const [notes, setNotes] = useState('')

  const today = new Date().toISOString().slice(0, 10)
  const weekAgo = new Date(Date.now() - 7 * 864e5).toISOString().slice(0, 10)

  const load = () => {
    api.get('/planner').then(setEntries).catch(e => setError(e.message))
    api.get(`/planner/daily?from=${weekAgo}&to=${today}`).then(setDaily).catch(() => {})
    api.get(`/planner/macros?from=${weekAgo}&to=${today}`).then(setMacros).catch(() => {})
  }

  useEffect(() => {
    load()
    api.get('/posts/feed').then(setFeed).catch(() => {})
  }, [])

  const log = async () => {
    setError(null)
    try {
      await api.post('/planner', {
        notes,
        postIds: pick.map(Number),
        consumed: true
      })
      setNotes('')
      setPick([])
      load()
    } catch (e) { setError(e.message) }
  }

  return (
    <div>
      {error && <div className="error">{error}</div>}

      <div className="card">
        <h3>Log a meal</h3>
        <div className="stack" style={{ maxWidth: 520 }}>
          <input placeholder="notes, e.g. Lunch at home"
                 value={notes} onChange={e => setNotes(e.target.value)} />
          <select multiple size={5} value={pick}
                  onChange={e => setPick(Array.from(e.target.selectedOptions, o => o.value))}>
            {feed.map(p => (
              <option key={p.id} value={p.id}>
                {p.recipeName} — {p.kcalPerServing} kcal / portion
              </option>
            ))}
          </select>
          <span className="muted">One portion is counted per selected meal.</span>
          <button className="primary" onClick={log}>Log it</button>
        </div>
      </div>

      <div className="card">
        <h3>Last 7 days</h3>
        <table>
          <thead><tr><th>day</th><th>kcal</th><th>meals</th></tr></thead>
          <tbody>
            {daily.map(d => (
              <tr key={d.day}>
                <td>{d.day}</td><td className="kcal">{d.kcalConsumed}</td><td>{d.mealsLogged}</td>
              </tr>
            ))}
          </tbody>
        </table>
        {daily.length === 0 && <p className="muted">Nothing logged in the last week.</p>}
      </div>

      {macros.length > 0 && (
        <div className="card">
          <h3>Macronutrients supplied this week</h3>
          <table>
            <tbody>
              {macros.map(m => (
                <tr key={m.nutrient}><td>{m.nutrient}</td><td>{m.total} {m.unit}</td></tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      <div className="card">
        <h3>Diary</h3>
        <table>
          <tbody>
            {entries.map(e => (
              <tr key={e.id}>
                <td>{new Date(e.dateTime).toLocaleString()}</td>
                <td>{e.notes}</td>
                <td className="muted">{e.meals.join(', ')}</td>
                <td className="kcal">{e.kcal}</td>
                <td>{e.consumed ? 'eaten' : 'planned'}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  )
}
