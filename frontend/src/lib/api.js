// Thin wrapper over fetch: attaches the JWT, unwraps JSON, surfaces the
// backend's error message instead of a bare "500".

const TOKEN_KEY = 'nutritioneer.token'
const USER_KEY = 'nutritioneer.user'

export const auth = {
  token: () => localStorage.getItem(TOKEN_KEY),
  user: () => {
    const raw = localStorage.getItem(USER_KEY)
    return raw ? JSON.parse(raw) : null
  },
  save: (res) => {
    localStorage.setItem(TOKEN_KEY, res.token)
    localStorage.setItem(USER_KEY, JSON.stringify({
      email: res.email, username: res.username, role: res.role
    }))
  },
  clear: () => {
    localStorage.removeItem(TOKEN_KEY)
    localStorage.removeItem(USER_KEY)
  }
}

async function request(method, path, body) {
  const headers = { 'Content-Type': 'application/json' }
  const token = auth.token()
  if (token) headers.Authorization = `Bearer ${token}`

  const res = await fetch(`/api${path}`, {
    method,
    headers,
    body: body === undefined ? undefined : JSON.stringify(body)
  })

  if (res.status === 204) return null

  const text = await res.text()
  const data = text ? JSON.parse(text) : null

  if (res.status === 401) {
    // A 401 while signing in means the credentials were rejected. Only treat
    // it as an expired session if we actually sent a token.
    if (token) {
      auth.clear()
      throw new Error(data?.message || 'Session expired, please sign in again')
    }
    throw new Error(data?.message || 'Invalid email or password')
  }

  if (!res.ok) throw new Error(data?.message || `Request failed (${res.status})`)
  return data
}

export const api = {
  get: (p) => request('GET', p),
  post: (p, b) => request('POST', p, b),
  patch: (p, b) => request('PATCH', p, b),
  del: (p) => request('DELETE', p)
}
