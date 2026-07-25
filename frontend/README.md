# Frontend - Enterprise Management System

Modern React + TypeScript + Material-UI frontend for the Enterprise Management System.

## 🚀 Quick Start

### Prerequisites
- Node.js 18+ 
- npm or yarn

### Installation

```bash
# Install dependencies
npm install

# or
yarn install
```

### Development Server

```bash
npm run dev
```

Access at `http://localhost:5173`

### Build for Production

```bash
npm run build
```

### Preview Production Build

```bash
npm run preview
```

## 📁 Project Structure

```
frontend/
├── src/
│   ├── components/          # Reusable components
│   ├── pages/               # Page components
│   ├── services/            # API services
│   ├── store/               # Zustand stores
│   ├── types/               # TypeScript types
│   ├── routes/              # Route definitions
│   ├── theme.ts             # Material-UI theme
│   ├── App.tsx              # Main App component
│   ├── main.tsx             # Entry point
│   └── index.css            # Global styles
├── index.html
├── package.json
├── tsconfig.json
├── vite.config.ts
└── README.md
```

## 🔧 Configuration

### Environment Variables

Create `.env.local`:

```bash
VITE_API_URL=http://localhost:8080/api
```

### API Proxy

Vite proxy configured in `vite.config.ts` for development.

## 📦 Dependencies

- **React 18** - UI library
- **React Router 6** - Routing
- **Material-UI 5** - Component library
- **Axios** - HTTP client
- **Zustand** - State management
- **TypeScript** - Type safety
- **Vite** - Build tool

## 🎨 Features

- ✅ JWT Authentication
- ✅ Role-based Access Control
- ✅ Employee Management
- ✅ Project Management
- ✅ Task Management
- ✅ Attendance Tracking
- ✅ Leave Management
- ✅ Dashboard with Analytics
- ✅ Responsive Design
- ✅ Dark Mode Support (coming soon)

## 📖 Pages

### Public Pages
- `/login` - User login
- `/register` - User registration

### Protected Pages
- `/dashboard` - Dashboard
- `/employees` - Employee list
- `/projects` - Project list
- `/tasks` - Task list
- `/attendance` - Attendance records
- `/leaves` - Leave requests
- `/profile` - User profile
- `/settings` - Settings

## 🛡️ Security

- JWT token stored securely
- Protected routes with role-based access
- API request interceptors for authentication
- CSRF protection
- Secure password reset flow

## 🚀 Deployment

### Vercel

1. Connect GitHub repository
2. Set environment variables
3. Deploy

```bash
VITE_API_URL=https://your-backend.com/api
```

### Netlify

1. Connect GitHub repository
2. Build command: `npm run build`
3. Publish directory: `dist`
4. Set environment variables

### Docker

```dockerfile
FROM node:18-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```

## 🧪 Testing

```bash
npm run test
```

## 📝 Code Quality

```bash
# Lint
npm run lint

# Format
npm run format

# Type check
npm run type-check
```

## 🤝 Contributing

1. Create feature branch: `git checkout -b feature/new-feature`
2. Commit changes: `git commit -am 'Add feature'`
3. Push branch: `git push origin feature/new-feature`
4. Create Pull Request

## 📄 License

MIT License

## 📧 Support

For issues and questions:
- GitHub Issues: [Create Issue](https://github.com/mdsameer1/Enterprise-management-system-/issues)
- Email: support@enterprise.com
