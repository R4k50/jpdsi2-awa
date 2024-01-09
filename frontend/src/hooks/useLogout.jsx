import { useState } from 'react';
import useAuthContext from './useAuthContext';
import useCartContext from './useCartContext';
import { useNavigate } from 'react-router-dom';


export default function useLogout() {
  const [isLoading, setIsLoading] = useState(false);
  const { dispatch: authDispatch } = useAuthContext();
  const { dispatch: cartDispatch } = useCartContext();
  const navigate = useNavigate();

  const logout = async () => {
    setIsLoading(() => true);

    authDispatch({ type: 'LOGOUT' });
    cartDispatch({ type: 'DESTROY' });

    setIsLoading(() => false);

    navigate('/');
  }

  return { logout, isLoading }
}