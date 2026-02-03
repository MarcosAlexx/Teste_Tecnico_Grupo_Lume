const API = 'http://localhost:8080'

function getToken() {
  return localStorage.getItem('accessToken')
}

function authHeaders() {
  return {
    'Content-Type': 'application/json',
    Authorization: `Bearer ${getToken()}`,
  }
}

export async function login(email, password) {
  const res = await fetch(`${API}/auth/login`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ email, password }),
  })

  if (!res.ok) throw new Error('Email ou senha inválidos')
  const data = await res.json()
  localStorage.setItem('accessToken', data.accessToken)
  localStorage.setItem('refreshToken', data.refreshToken)
  return data
}

export function logout() {
  localStorage.removeItem('accessToken')
  localStorage.removeItem('refreshToken')
}

export function isAuthenticated() {
  return !!localStorage.getItem('accessToken')
}

export async function listarClientes() {
  const res = await fetch(`${API}/clientes/listar`, { headers: authHeaders() })
  if (res.status === 403) throw new Error('Sessão expirada')
  if (!res.ok) throw new Error('Erro ao listar clientes')
  return res.json()
}

export async function buscarClientePorId(id) {
  const res = await fetch(`${API}/clientes/id/${id}`, { headers: authHeaders() })
  if (!res.ok) throw new Error('Cliente não encontrado')
  return res.json()
}

export async function buscarClientePorCpf(cpf) {
  const res = await fetch(`${API}/clientes/cpf/${cpf}`, { headers: authHeaders() })
  if (!res.ok) throw new Error('Cliente não encontrado')
  return res.json()
}

export async function criarCliente(nome, cpf, cep) {
  const res = await fetch(`${API}/clientes/criar`, {
    method: 'POST',
    headers: authHeaders(),
    body: JSON.stringify({ nome, cpf, cep }),
  })
  if (!res.ok) {
    const text = await res.text()
    if (text.includes('UK7wflw78ibh162cmq12ii6ffly') || text.includes('unique') || text.includes('Unique') || text.includes('cpf')) {
      throw new Error('CPF já cadastrado')
    }
    throw new Error(text || 'Erro ao criar cliente')
  }
  return res.json()
}

export async function atualizarCliente(id, nome, cpf, cep) {
  const res = await fetch(`${API}/clientes/atualizar/${id}`, {
    method: 'PUT',
    headers: authHeaders(),
    body: JSON.stringify({ nome, cpf, cep }),
  })
  if (!res.ok) throw new Error('Erro ao atualizar cliente')
  return res.json()
}

export async function deletarCliente(id) {
  const res = await fetch(`${API}/clientes/deletar/${id}`, {
    method: 'DELETE',
    headers: authHeaders(),
  })
  if (!res.ok) throw new Error('Erro ao deletar cliente')
}
