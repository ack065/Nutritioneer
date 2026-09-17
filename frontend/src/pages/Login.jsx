import { useState } from 'react'
import { api, auth } from '../lib/api.js'

export default function Login({ onSignedIn }) {
  const [mode, setMode] = useState('login')
  const [form, setForm] = useState({ email: '', username: '', password: '' })
  const [error, setError] = useState(null)
  const [busy, setBusy] = useState(false)

  const set = (k) => (e) => setForm({ ...form, [k]: e.target.value })

  const submit = async () => {
    setBusy(true)
    setError(null)
    try {
      const body = mode === 'login'
        ? { email: form.email, password: form.password }
        : form
      const res = await api.post(`/auth/${mode}`, body)
      auth.save(res)
      onSignedIn(auth.user())
    } catch (e) {
      setError(e.message)
    } finally {
      setBusy(false)
    }
  }

  return (
    <div className="app">
      <header className="top"><h1>Nutritioneer</h1><span className="muted">prototype</span></header>

      <div className="card" style={{ maxWidth: 440 }}>
        <h3>{mode === 'login' ? 'Sign in' : 'Create an account'}</h3>
        {error && <div className="error">{error}</div>}

        <div className="stack">
          <input placeholder="email" value={form.email} onChange={set('email')} />
          {mode === 'register' &&
            <input placeholder="username" value={form.username} onChange={set('username')} />}
          <input placeholder="password" type="password"
                 value={form.password} onChange={set('password')} />

          <div className="row">
            <button className="primary" onClick={submit} disabled={busy}>
              {busy ? 'working...' : mode === 'login' ? 'Sign in' : 'Register'}
            </button>
            <button className="small"
                    onClick={() => { setMode(mode === 'login' ? 'register' : 'login'); setError(null) }}>
              {mode === 'login' ? 'need an account?' : 'have an account?'}
            </button>
          </div>
        </div>

        <p className="muted" style={{ marginTop: 14 }}>
          Seed accounts use the password <code>testPass@1</code>, for example{' '}
          <code>marija.trajkovska@gmail.com</code> or <code>admin@nutritioneer.mk</code>.
        </p>
      </div>
    </div>
  )
}
