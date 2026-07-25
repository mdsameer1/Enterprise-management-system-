import React, { useState } from 'react'
import {
  Box,
  Container,
  TextField,
  Button,
  Typography,
  Paper,
  Alert,
} from '@mui/material'
import { useNavigate } from 'react-router-dom'

const Login: React.FC = () => {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')
  const navigate = useNavigate()

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    // Placeholder authentication logic
    setError('Authentication service not yet implemented')
  }

  return (
    <Container maxWidth="sm">
      <Box
        sx={{
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center',
          minHeight: '100vh',
        }}
      >
        <Paper sx={{ width: '100%', p: 4 }}>
          <Typography variant="h4" component="h1" gutterBottom align="center">
            Enterprise Management System
          </Typography>
          <Typography variant="body2" color="textSecondary" align="center" gutterBottom>
            Sign in to your account
          </Typography>

          {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}

          <Box component="form" onSubmit={handleSubmit} sx={{ mt: 3 }}>
            <TextField
              fullWidth
              label="Email"
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              margin="normal"
              required
            />
            <TextField
              fullWidth
              label="Password"
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              margin="normal"
              required
            />
            <Button
              fullWidth
              variant="contained"
              sx={{ mt: 3 }}
              type="submit"
            >
              Sign In
            </Button>
          </Box>
        </Paper>
      </Box>
    </Container>
  )
}

export default Login
