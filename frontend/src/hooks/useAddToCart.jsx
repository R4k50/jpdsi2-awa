import { useState } from 'react';
import useCartContext from './useCartContext';

export default function useAddToCart() {
  const [error, setError] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const { dispatch } = useCartContext();

  const addToCart = id => {
    setIsLoading(() => true);
    setError(null);

    try {
      dispatch({ type: 'PUT', payload: id });
      setIsLoading(() => false);
    }
    catch (error) {
      setError(error);
      setIsLoading(() => false);
    }
  }

  return { addToCart, isLoading, error }
}