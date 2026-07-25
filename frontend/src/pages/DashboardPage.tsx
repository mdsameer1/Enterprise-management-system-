import { Box, Container, Typography } from '@mui/material';
import { useAuthStore } from '../store/authStore';

export function DashboardPage() {
  const user = useAuthStore((state) => state.user);

  return (
    <Container maxWidth="lg">
      <Box sx={{ py: 4 }}>
        <Typography variant="h3" component="h1" sx={{ mb: 3 }}>
          Dashboard
        </Typography>
        <Typography>
          Welcome, {user?.username}!
        </Typography>
      </Box>
    </Container>
  );
}
