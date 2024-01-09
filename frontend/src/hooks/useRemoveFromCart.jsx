import { useState } from 'react';
import useCartContext from './useCartContext';

export default function useRemoveFromCart() {
  const [error, setError] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const { dispatch } = useCartContext();

  const removeFromCart = id => {
    setIsLoading(() => true);
    setError(null);

    try {
      dispatch({ type: 'REMOVE', payload: id });
      setIsLoading(() => false);
    }
    catch (error) {
      setError(error);
      setIsLoading(() => false);
    }
  }

  return { removeFromCart, isLoading, error }
}