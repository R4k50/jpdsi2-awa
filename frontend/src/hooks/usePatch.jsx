import { useNavigate } from 'react-router-dom'
import { useState } from 'react';
import axios from 'axios';
import useLogout from './useLogout';


export default function usePatch(url, contentType = 'application/json') {
  const [errors, setErrors] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const navigate = useNavigate();
  const { logout } = useLogout();

  const patchById = async (data, id, redirect = '/') => {
    const { userData } = JSON.parse(localStorage.getItem('user')) ?? '';
    setIsLoading(() => true);
    setErrors(null);

    axios.patch(`${url}/${id}`, data, {
      headers: {
        'Content-Type': contentType,
        "Authorization": `Bearer ${userData?.token}`,
        'Accept': 'application/json'
      },
      timeout: 5000
    })
      .then(() => {
        navigate(redirect);
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

  return { patchById, isLoading, errors }
}