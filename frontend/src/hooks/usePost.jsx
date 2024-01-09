import { useState } from 'react';
import axios from 'axios';
import useLogout from './useLogout';


export default function usePost(path, contentType = 'application/json') {
  const [errors, setErrors] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const { logout } = useLogout();

  const post = async (data) => {
    const { userData } = JSON.parse(localStorage.getItem('user')) ?? '';
    setIsLoading(() => true);
    setErrors(null);

    if (!userData?.token) {
      setIsLoading(() => false);
      return;
    }

    axios.post(path, data, {
      headers: {
        'Content-Type': contentType,
        "Authorization": `Bearer ${userData?.token}`,
        'Accept': 'application/json'
      },
      timeout: 5000
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

        if (!response?.data?.errors) {
          setErrors(() => [response.statusText]);
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

  return { post, isLoading, errors }
}