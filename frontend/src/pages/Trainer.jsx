import { useEffect, useState } from 'react'
import { api } from '../lib/api.js'

export default function Trainer() {
  const [email, setEmail] = useState('marija.trajkovska@gmail.com')
  const [from, setFrom] = useState('2026-01-20')
  const [to, setTo] = useState('2026-02-01')
  const [intake, setIntake] = useState([])
  const [macros, setMacros] = useState([])
  const [progress, setProgress] = useState([])
  const [density, setDensity] = useState([])
  const [nutrient, setNutrient] = useState('Protein')
  const [error, setError] = useState(null)

  const loadClient = async () => {
    setError(null)
    try {
      setIntake(await api.get(`/trainer/clients/${email}/intake?from=${from}&to=${to}`))
      setMacros(await api.get(`/trainer/clients/${email}/macros?from=${from}&to=${to}`))
      setProgress(await api.get(`/trainer/clients/${email}/progress`))
    } catch (e) { setError(e.message) }
  }

  const loadDensity = () =>
    api.get(`/trainer/analysis/nutrient-density?nutrient=${nutrient}&limit=8`)
      .then(setDensity).catch(e => setError(e.message))

  useEffect(() => { loadDensity() }, [])

  return (
    <div>
      {error && <div className="error">{error}</div>}

      <div className="card">
        <h3>Client report</h3>
        <div className="row">
          <input style={{ flex: 1, minWidth: 240 }} value={email}
                 onChange={e => setEmail(e.target.value)} placeholder="client email" />
          <input type="date" value={from} onChange={e => setFrom(e.target.value)} />
          <input type="date" value={to} onChange={e => setTo(e.target.value)} />
          <button className="primary" onClick={loadClient}>Load</button>
        </div>
      </div>

      {intake.length > 0 && (
        <div className="card">
          <h3>Daily intake</h3>
          <table>
            <thead><tr><th>day</th><th>kcal</th><th>meals</th></tr></thead>
            <tbody>
              {intake.map(d => (
                <tr key={d.day}><td>{d.day}</td>
                  <td className="kcal">{d.kcalConsumed}</td><td>{d.mealsLogged}</td></tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {macros.length > 0 && (
        <div className="card">
          <h3>Macronutrients over the period</h3>
          <table>
            <tbody>
              {macros.map(m => (
                <tr key={m.nutrient}><td>{m.nutrient}</td><td>{m.total} {m.unit}</td></tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {progress.length > 0 && (
        <div className="card">
          <h3>Biometric progress</h3>
          <table>
            <thead><tr><th>date</th><th>weight</th><th>change</th><th>BMI</th></tr></thead>
            <tbody>
              {progress.map(p => (
                <tr key={p.id}><td>{p.date}</td><td>{p.weight}</td>
                  <td>{p.changeSincePrevious ?? '—'}</td><td>{p.bmi ?? '—'}</td></tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      <div className="card">
        <h3>Recipe analysis</h3>
        <div className="row" style={{ marginBottom: 8 }}>
          <input value={nutrient} onChange={e => setNutrient(e.target.value)}
                 placeholder="nutrient, e.g. Protein" />
          <button className="primary" onClick={loadDensity}>Rank</button>
        </div>
        <table>
          <thead>
            <tr><th>recipe</th><th>kcal/portion</th><th>per portion</th><th>per 100 kcal</th></tr>
          </thead>
          <tbody>
            {density.map(d => (
              <tr key={d.recipe}>
                <td>{d.recipe}</td><td>{d.kcalPerServing}</td>
                <td>{d.amountPerServing}</td><td className="kcal">{d.amountPer100Kcal}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  )
}
