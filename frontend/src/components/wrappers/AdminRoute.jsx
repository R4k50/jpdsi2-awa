import { useEffect } from 'react';
import { Navigate } from 'react-router-dom'
import useAuthContext from '../../hooks/useAuthContext';
import ProtectedRoute from './ProtectedRoute';

export default function AdminRoute({ children, to = '/'}) {
  const { userData } = useAuthContext();

  useEffect(() => {
    let isAdmin = userData?.roles?.includes("ROLE_ADMIN");

    if (isAdmin == false)
      children = <Navigate to={to} />
  }, [userData]);

  return (
    <ProtectedRoute>
      { children }
    </ProtectedRoute>
  );
}