import { useEffect, useState } from 'react'
import { api } from '../lib/api.js'

export default function Grocery() {
  const [lists, setLists] = useState([])
  const [recipes, setRecipes] = useState([])
  const [catalog, setCatalog] = useState([])
  const [open, setOpen] = useState(null)
  const [error, setError] = useState(null)
  const [notes, setNotes] = useState('')
  const [bulk, setBulk] = useState([])
  const [item, setItem] = useState({ ingredientName: '', buyQuantity: '' })

  const load = () => api.get('/grocery-lists').then(setLists).catch(e => setError(e.message))

  useEffect(() => {
    load()
    api.get('/recipes').then(setRecipes).catch(() => {})
    api.get('/catalog/ingredients').then(setCatalog).catch(() => {})
  }, [])

  const create = async () => {
    setError(null)
    try {
      const created = await api.post('/grocery-lists', {
        notes,
        bulkRecipeIds: bulk.map(Number),
        singleItems: item.ingredientName && item.buyQuantity
          ? [{ ingredientName: item.ingredientName, buyQuantity: Number(item.buyQuantity) }]
          : []
      })
      setOpen(created)
      setNotes(''); setBulk([]); setItem({ ingredientName: '', buyQuantity: '' })
      load()
    } catch (e) { setError(e.message) }
  }

  return (
    <div>
      {error && <div className="error">{error}</div>}

      <div className="card">
        <h3>New grocery list</h3>
        <div className="stack" style={{ maxWidth: 520 }}>
          <input placeholder="notes" value={notes} onChange={e => setNotes(e.target.value)} />
          <span className="muted">Add whole recipes:</span>
          <select multiple size={5} value={bulk}
                  onChange={e => setBulk(Array.from(e.target.selectedOptions, o => o.value))}>
            {recipes.map(r => <option key={r.id} value={r.id}>{r.name}</option>)}
          </select>
          <span className="muted">Plus one item on its own:</span>
          <div className="row">
            <select style={{ flex: 1 }} value={item.ingredientName}
                    onChange={e => setItem({ ...item, ingredientName: e.target.value })}>
              <option value="">— ingredient —</option>
              {catalog.map(c => <option key={c.name} value={c.name}>{c.name}</option>)}
            </select>
            <input type="number" placeholder="grams" style={{ width: 110 }}
                   value={item.buyQuantity}
                   onChange={e => setItem({ ...item, buyQuantity: e.target.value })} />
          </div>
          <button className="primary" onClick={create}>Create list</button>
        </div>
      </div>

      {open && (
        <div className="card">
          <h3>Shopping list #{open.id}</h3>
          <div className="muted">
            {open.notes} · total <span className="kcal">{open.kcal} kcal</span>
            {open.bulkRecipes.length > 0 && <> · from: {open.bulkRecipes.join(', ')}</>}
          </div>
          <table>
            <thead><tr><th>category</th><th>ingredient</th><th>grams</th></tr></thead>
            <tbody>
              {open.shoppingLines.map(l => (
                <tr key={l.ingredient}>
                  <td className="muted">{l.category}</td>
                  <td>{l.ingredient}</td>
                  <td>{l.totalGrams}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      <div className="card">
        <h3>My lists</h3>
        <table>
          <tbody>
            {lists.map(l => (
              <tr key={l.id}>
                <td>{new Date(l.dateTime).toLocaleDateString()}</td>
                <td>{l.notes}</td>
                <td className="kcal">{l.kcal} kcal</td>
                <td>{l.bought ? 'bought' : 'open'}</td>
                <td style={{ width: 1 }}>
                  <button className="small" onClick={() => api.get(`/grocery-lists/${l.id}`).then(setOpen)}>
                    open
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  )
}
