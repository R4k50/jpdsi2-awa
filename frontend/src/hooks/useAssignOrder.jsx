import { useState } from 'react';
import axios from 'axios';
import useLogout from './useLogout';


export default function useAssignOrder() {
  const [errors, setErrors] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const { logout } = useLogout();

  const assign = async (id) => {
    const { userData } = JSON.parse(localStorage.getItem('user')) ?? '';
    setIsLoading(() => true);
    setErrors(null);

    await axios.patch(`/order/${id}/assign`, id, {
      timeout: 5000,
      headers: {
        'Content-Type': 'application/json',
        "Authorization": `Bearer ${userData?.token}`
      }
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

  return { assign, isLoading, errors }
}