import { useState } from 'react'
import { auth } from './lib/api.js'
import Login from './pages/Login.jsx'
import Feed from './pages/Feed.jsx'
import Recipes from './pages/Recipes.jsx'
import Planner from './pages/Planner.jsx'
import Grocery from './pages/Grocery.jsx'
import Biometrics from './pages/Biometrics.jsx'
import Trainer from './pages/Trainer.jsx'

const TABS = [
  { key: 'feed', label: 'Feed', component: Feed },
  { key: 'recipes', label: 'Recipes', component: Recipes },
  { key: 'planner', label: 'Planner', component: Planner },
  { key: 'grocery', label: 'Grocery', component: Grocery },
  { key: 'biometrics', label: 'Biometrics', component: Biometrics },
  { key: 'trainer', label: 'Trainer', component: Trainer, roles: ['trainer', 'administrator'] }
]

export default function App() {
  const [user, setUser] = useState(auth.user())
  const [tab, setTab] = useState('feed')

  if (!user) return <Login onSignedIn={setUser} />

  const visible = TABS.filter(t => !t.roles || t.roles.includes(user.role))
  const Active = (visible.find(t => t.key === tab) || visible[0]).component

  const signOut = () => {
    auth.clear()
    setUser(null)
  }

  return (
    <div className="app">
      <header className="top">
        <h1>Nutritioneer</h1>
        <span className="muted">prototype</span>
        <span className="who">
          {user.username} ({user.role}) <button className="small" onClick={signOut}>sign out</button>
        </span>
      </header>

      <nav>
        {visible.map(t => (
          <button key={t.key}
                  className={t.key === tab ? 'active' : ''}
                  onClick={() => setTab(t.key)}>
            {t.label}
          </button>
        ))}
      </nav>

      <Active user={user} />
    </div>
  )
}
