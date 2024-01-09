import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import useCartContext from './useCartContext';
import axios from 'axios';
import useLogout from './useLogout';


export default function useOrder() {
  const [errors, setErrors] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const { dispatch: cartDispatch } = useCartContext();
  const navigate = useNavigate();
  const { logout } = useLogout();

  const order = async (orderData) => {
    const { userData } = JSON.parse(localStorage.getItem('user')) ?? '';
    setIsLoading(() => true);
    setErrors(null);

    axios.post('/order', orderData, {
      headers: {
        'Content-Type': 'application/json',
        "Authorization": `Bearer ${userData?.token}`
      },
      timeout: 5000
    })
      .then(() => {
        cartDispatch({ type: 'DESTROY' });
        navigate('/orders');
      })
      .catch(({ response }) => {
        if (!response) {
          setErrors(() => ['Network Error']);
          return;
        }

        if (response.status === 401) {
          logout();
          return;
        }

        if (response.status != 200) {
          setErrors(response.data.errors);
          return;
        }
      })
      .finally(() => {
        setIsLoading(() => false);
      });
  }

  return { order, isLoading, errors }
}