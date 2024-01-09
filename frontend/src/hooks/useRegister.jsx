import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import useAuthContext from './useAuthContext';
import axios from 'axios';

export default function useRegister() {
  const [errors, setErrors] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const navigate = useNavigate();
  const { dispatch } = useAuthContext();

  const register = async (user) => {
    setIsLoading(() => true);
    setErrors(null);

    axios.post('/register', user, { timeout: 5000 })
      .then(({ data }) => {
        dispatch({ type: 'REGISTER', payload: data });
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
      });;
  }

  return { register, isLoading, errors }
}