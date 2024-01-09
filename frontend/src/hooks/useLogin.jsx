import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import useAuthContext from './useAuthContext';
import axios from 'axios';

export default function useLogin() {
  const [errors, setErrors] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const navigate = useNavigate();
  const { dispatch } = useAuthContext();

  const login = async (credentials) => {
    setIsLoading(() => true);
    setErrors(null);

    axios.post('/login', credentials, { timeout: 5000 })
      .then(({ data }) => {
        dispatch({ type: 'LOGIN', payload: data });
        navigate('/');
      })
      .catch(({ response }) => {
        if (!response) {
          setErrors(() => ['Network Error']);
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

  return { login, isLoading, errors }
}