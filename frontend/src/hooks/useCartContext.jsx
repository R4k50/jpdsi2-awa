import { CartContext } from "../context/CartContext";
import { useContext } from "react";

export default function useCartContext() {
  const context = useContext(CartContext);

  if(!context)
    throw Error('useCartContext must be inside the CartContextProvider');

  return context;
}