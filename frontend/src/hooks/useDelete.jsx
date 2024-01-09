import { useState } from 'react';
import axios from 'axios';
import useLogout from './useLogout';


export default function useDelete(url) {
  const [errors, setErrors] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const { logout } = useLogout();

  const deleteById = async (id, message) => {
    const { userData } = JSON.parse(localStorage.getItem('user')) ?? '';
    setIsLoading(() => true);
    setErrors(null);

    if (!confirm((message ?? `Please confirm that you want to delete this item`) + ` (id: ${id})`)) {
      setIsLoading(() => false);
      return false;
    }

    await axios.delete(`${url}/${id}`, {
      headers: {
        'Content-Type': 'application/json',
        "Authorization": `Bearer ${userData?.token}`
      },
      timeout: 5000
    })
      .catch(({ response }) => {
        if (!response) {
          setErrors(() => ['Network Error']);
          return false;
        }

        if (response.status === 401) {
          logout();
          return false;
        }

        if (response.status != 200) {
          setErrors(response.data.errors);
          return;
        }
      })
      .finally(() => {
        setIsLoading(() => false);
      });

      return true;
  }

  return { deleteById, isLoading, errors }
}