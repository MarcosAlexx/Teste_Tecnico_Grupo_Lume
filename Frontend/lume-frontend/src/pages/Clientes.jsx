import { useState, useEffect } from 'react'
import {
  listarClientes,
  criarCliente,
  atualizarCliente,
  deletarCliente,
  buscarClientePorCpf,
  buscarClientePorId,
} from '../api'

function formatCpf(value) {
  const digits = value.replace(/\D/g, '').slice(0, 11)
  return digits
    .replace(/(\d{3})(\d)/, '$1.$2')
    .replace(/(\d{3})(\d)/, '$1.$2')
    .replace(/(\d{3})(\d{1,2})$/, '$1-$2')
}

function formatCep(value) {
  const digits = value.replace(/\D/g, '').slice(0, 8)
  return digits.replace(/(\d{2})(\d{3})(\d{1,3})/, '$1.$2-$3')
}

function stripMask(value) {
  return value.replace(/\D/g, '')
}

export default function Clientes() {
  const [clientes, setClientes] = useState([])
  const [error, setError] = useState('')
  const [success, setSuccess] = useState('')

  // Create form
  const [nome, setNome] = useState('')
  const [cpf, setCpf] = useState('')
  const [cep, setCep] = useState('')
  const [creating, setCreating] = useState(false)

  // Search
  const [searchCpf, setSearchCpf] = useState('')
  const [searchResult, setSearchResult] = useState(null)
  const [searchId, setSearchId] = useState('')
  const [searchIdResult, setSearchIdResult] = useState(null)

  // Edit modal
  const [editing, setEditing] = useState(null)
  const [editNome, setEditNome] = useState('')
  const [editCpf, setEditCpf] = useState('')
  const [editCep, setEditCep] = useState('')

  function clearMessages() {
    setError('')
    setSuccess('')
  }

  async function loadClientes() {
    try {
      const data = await listarClientes()
      setClientes(data)
    } catch (err) {
      setError(err.message)
    }
  }

  useEffect(() => { loadClientes() }, [])

  async function handleCreate(e) {
    e.preventDefault()
    clearMessages()
    if (stripMask(cpf).length !== 11) {
      setError('Digite um CPF válido')
      return
    }
    if (stripMask(cep).length !== 8) {
      setError('Digite um CEP válido')
      return
    }
    setCreating(true)
    try {
      await criarCliente(nome, stripMask(cpf), stripMask(cep))
      setSuccess('Cliente criado com sucesso!')
      setNome(''); setCpf(''); setCep('')
      loadClientes()
    } catch (err) {
      setError(err.message)
    } finally {
      setCreating(false)
    }
  }

  async function handleDelete(id) {
    if (!confirm('Tem certeza que deseja deletar este cliente?')) return
    clearMessages()
    try {
      await deletarCliente(id)
      setSuccess('Cliente deletado!')
      loadClientes()
    } catch (err) {
      setError(err.message)
    }
  }

  function openEdit(cliente) {
    setEditing(cliente)
    setEditNome(cliente.nome)
    setEditCpf(formatCpf(cliente.cpf || ''))
    setEditCep(formatCep(cliente.endereco?.cep || ''))
  }

  async function handleUpdate(e) {
    e.preventDefault()
    clearMessages()
    try {
      await atualizarCliente(editing.id, editNome, stripMask(editCpf), stripMask(editCep))
      setSuccess('Cliente atualizado!')
      setEditing(null)
      loadClientes()
    } catch (err) {
      setError(err.message)
    }
  }

  async function handleSearch(e) {
    e.preventDefault()
    clearMessages()
    setSearchResult(null)
    if (!searchCpf.trim()) return
    try {
      const data = await buscarClientePorCpf(stripMask(searchCpf))
      setSearchResult(data)
    } catch (err) {
      setError(err.message)
    }
  }

  async function handleSearchById(e) {
    e.preventDefault()
    clearMessages()
    setSearchIdResult(null)
    if (!searchId.trim()) return
    try {
      const data = await buscarClientePorId(searchId.trim())
      setSearchIdResult(data)
    } catch (err) {
      setError(err.message)
    }
  }

  function formatEndereco(end) {
    if (!end) return '-'
    return (
      <span className="endereco-cell">
        {end.logradouro}, {end.bairro}<br />
        {end.cidade} - {end.uf}, {end.cep}
      </span>
    )
  }

  return (
    <div className="page">
      <h1>Clientes</h1>

      {error && <div className="alert alert-error">{error}</div>}
      {success && <div className="alert alert-success">{success}</div>}

      {/* Create */}
      <div className="card">
        <h3>Novo Cliente</h3>
        <form className="inline-form" onSubmit={handleCreate}>
          <div className="form-group">
            <label>Nome</label>
            <input value={nome} onChange={e => setNome(e.target.value)} required />
          </div>
          <div className="form-group">
            <label>CPF</label>
            <input
              value={cpf}
              onChange={e => setCpf(formatCpf(e.target.value))}
              placeholder="000.000.000-00"
              maxLength={14}
              required
            />
          </div>
          <div className="form-group">
            <label>CEP</label>
            <input
              value={cep}
              onChange={e => setCep(formatCep(e.target.value))}
              placeholder="00.000-000"
              maxLength={10}
              required
            />
          </div>
          <button className="btn-primary" type="submit" disabled={creating}>
            {creating ? 'Criando...' : 'Criar'}
          </button>
        </form>
      </div>

      {/* Search by ID */}
      <div className="card">
        <h3>Buscar por ID</h3>
        <form className="search-section" onSubmit={handleSearchById}>
          <input
            value={searchId}
            onChange={e => setSearchId(e.target.value)}
            placeholder="Digite o ID"
            type="number"
            min="1"
          />
          <button className="btn-secondary btn-sm" type="submit">Buscar</button>
          {searchIdResult && (
            <button
              className="btn-sm"
              type="button"
              style={{ background: 'transparent', color: '#6c757d' }}
              onClick={() => { setSearchIdResult(null); setSearchId('') }}
            >
              Limpar
            </button>
          )}
        </form>
        {searchIdResult && (
          <div className="table-wrapper">
            <table>
              <thead>
                <tr>
                  <th>ID</th><th>Nome</th><th>CPF</th><th>Endereco</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>{searchIdResult.id}</td>
                  <td>{searchIdResult.nome}</td>
                  <td>{formatCpf(searchIdResult.cpf || '')}</td>
                  <td>{formatEndereco(searchIdResult.endereco)}</td>
                </tr>
              </tbody>
            </table>
          </div>
        )}
      </div>

      {/* Search by CPF */}
      <div className="card">
        <h3>Buscar por CPF</h3>
        <form className="search-section" onSubmit={handleSearch}>
          <input
            value={searchCpf}
            onChange={e => setSearchCpf(formatCpf(e.target.value))}
            placeholder="000.000.000-00"
            maxLength={14}
          />
          <button className="btn-secondary btn-sm" type="submit">Buscar</button>
          {searchResult && (
            <button
              className="btn-sm"
              type="button"
              style={{ background: 'transparent', color: '#6c757d' }}
              onClick={() => { setSearchResult(null); setSearchCpf('') }}
            >
              Limpar
            </button>
          )}
        </form>
        {searchResult && (
          <div className="table-wrapper">
            <table>
              <thead>
                <tr>
                  <th>ID</th><th>Nome</th><th>CPF</th><th>Endereco</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>{searchResult.id}</td>
                  <td>{searchResult.nome}</td>
                  <td>{searchResult.cpf}</td>
                  <td>{formatEndereco(searchResult.endereco)}</td>
                </tr>
              </tbody>
            </table>
          </div>
        )}
      </div>

      {/* List */}
      <div className="card">
        <h3>
          Todos os Clientes
          <button
            className="btn-sm btn-secondary"
            style={{ marginLeft: 12 }}
            onClick={loadClientes}
          >
            Atualizar
          </button>
        </h3>
        {clientes.length === 0 ? (
          <div className="empty">Nenhum cliente cadastrado.</div>
        ) : (
          <div className="table-wrapper">
            <table>
              <thead>
                <tr>
                  <th>ID</th><th>Nome</th><th>CPF</th><th>Endereco</th><th>Acoes</th>
                </tr>
              </thead>
              <tbody>
                {clientes.map(c => (
                  <tr key={c.id}>
                    <td>{c.id}</td>
                    <td>{c.nome}</td>
                    <td>{formatCpf(c.cpf || '')}</td>
                    <td>{formatEndereco(c.endereco)}</td>
                    <td>
                      <div className="actions">
                        <button className="btn-warning btn-sm" onClick={() => openEdit(c)}>
                          Editar
                        </button>
                        <button className="btn-danger btn-sm" onClick={() => handleDelete(c.id)}>
                          Deletar
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>

      {/* Edit modal */}
      {editing && (
        <div className="modal-overlay" onClick={() => setEditing(null)}>
          <div className="modal" onClick={e => e.stopPropagation()}>
            <h3>Editar Cliente #{editing.id}</h3>
            <form onSubmit={handleUpdate}>
              <div className="form-group">
                <label>Nome</label>
                <input value={editNome} onChange={e => setEditNome(e.target.value)} required />
              </div>
              <div className="form-group">
                <label>CPF</label>
                <input value={editCpf} onChange={e => setEditCpf(formatCpf(e.target.value))} maxLength={14} required />
              </div>
              <div className="form-group">
                <label>CEP</label>
                <input value={editCep} onChange={e => setEditCep(formatCep(e.target.value))} maxLength={10} required />
              </div>
              <div className="modal-actions">
                <button className="btn-secondary" type="button" onClick={() => setEditing(null)}>
                  Cancelar
                </button>
                <button className="btn-primary" type="submit">Salvar</button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  )
}
