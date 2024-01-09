import { useEffect, useState } from 'react';
import axios from 'axios';
import useLogout from './useLogout';


export default function useFetch(defaultUrl) {
  const [url, setUrl] = useState(defaultUrl);
  const [data, setData] = useState(null);
  const [errors, setErrors] = useState(null);
  const [update, setUpdate] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const { logout } = useLogout();

  const fetchData = () => {
    const { userData } = JSON.parse(localStorage.getItem('user')) ?? '';
    setIsLoading(() => true);
    setErrors(null);

    axios.get(url, {
      headers: {
        'Content-Type': 'application/json',
        "Authorization": `Bearer ${userData?.token}`
      },
      timeout: 5000
    })
      .then((response) => {
        setData(() => response.data);
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

  const refetch = (newUrl) => {
    if (newUrl) {
      setUrl(() => newUrl);
    }

    setUpdate(prev => !prev);
  }

  useEffect(() => fetchData(), [update]);

  return { data, isLoading, errors, refetch }
}