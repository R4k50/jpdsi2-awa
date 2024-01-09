import { useEffect } from 'react';
import { Navigate } from 'react-router-dom'
import useAuthContext from '../../hooks/useAuthContext';

export default function ProtectedRoute({ children, to = '/'}) {
  const { userData } = useAuthContext();

  useEffect(() => {
    if (!userData)
      children = <Navigate to={to} />
  }, [userData]);

  return children;
}