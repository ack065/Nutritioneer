import { useEffect, useState } from 'react'
import { api } from '../lib/api.js'

export default function Biometrics() {
  const [rows, setRows] = useState([])
  const [error, setError] = useState(null)
  const [form, setForm] = useState({
    date: new Date().toISOString().slice(0, 10),
    weight: '', height: '', age: '', muscleFatRatio: ''
  })

  const load = () => api.get('/biometrics').then(setRows).catch(e => setError(e.message))
  useEffect(() => { load() }, [])

  const set = (k) => (e) => setForm({ ...form, [k]: e.target.value })

  const save = async () => {
    setError(null)
    try {
      await api.post('/biometrics', {
        date: form.date,
        weight: form.weight ? Number(form.weight) : null,
        height: form.height ? Number(form.height) : null,
        age: form.age ? Number(form.age) : null,
        muscleFatRatio: form.muscleFatRatio ? Number(form.muscleFatRatio) : null
      })
      load()
    } catch (e) { setError(e.message) }
  }

  return (
    <div>
      {error && <div className="error">{error}</div>}

      <div className="card">
        <h3>Record a measurement</h3>
        <p className="muted">One per day. Saving the same date again updates that entry.</p>
        <div className="row">
          <input type="date" value={form.date} onChange={set('date')} />
          <input type="number" step="0.1" placeholder="weight kg" style={{ width: 120 }}
                 value={form.weight} onChange={set('weight')} />
          <input type="number" placeholder="height cm" style={{ width: 120 }}
                 value={form.height} onChange={set('height')} />
          <input type="number" placeholder="age" style={{ width: 90 }}
                 value={form.age} onChange={set('age')} />
          <input type="number" step="0.01" placeholder="muscle/fat" style={{ width: 120 }}
                 value={form.muscleFatRatio} onChange={set('muscleFatRatio')} />
          <button className="primary" onClick={save}>Save</button>
        </div>
      </div>

      <div className="card">
        <h3>Progress</h3>
        <table>
          <thead>
            <tr><th>date</th><th>weight</th><th>change</th><th>BMI</th><th>muscle/fat</th><th></th></tr>
          </thead>
          <tbody>
            {rows.map(r => (
              <tr key={r.id}>
                <td>{r.date}</td>
                <td>{r.weight}</td>
                <td className={r.changeSincePrevious < 0 ? 'kcal' : 'muted'}>
                  {r.changeSincePrevious ?? '—'}
                </td>
                <td>{r.bmi ?? '—'}</td>
                <td>{r.muscleFatRatio ?? '—'}</td>
                <td style={{ width: 1 }}>
                  <button className="small"
                          onClick={() => api.del(`/biometrics/${r.id}`).then(load)}>delete</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        {rows.length === 0 && <p className="muted">No measurements yet.</p>}
      </div>
    </div>
  )
}
