import { createContext, useReducer, useEffect } from 'react';


const put = (products, productId) => {
  const productIndex = products.indexOf(
    products.find(({ id }) => id === productId)
  );

  const isProductAlreadyInCart = productIndex >= 0;

  if (!isProductAlreadyInCart) {
    products = [ ...products, { id: productId, quantity: 1 } ];

    localStorage.setItem('cart', JSON.stringify({ products }));
    return { products };
  }

  products = products.map((product, index) => (
    (index == productIndex)
      ? { id: product.id, quantity: product.quantity + 1 }
      : product
  ));

  localStorage.setItem('cart', JSON.stringify({ products }));
  return { products };
}

const remove = (products, productId) => {
  const [ quantity ] = products
    .filter(({ id }) => (id === productId))
    .map(({ quantity }) => quantity);

  if (quantity == 0)
    return { products };

  if (quantity == 1 || quantity < 0) {
    products = products.filter(({ id }) => (id !== productId));

    localStorage.setItem('cart', JSON.stringify({ products }));
    return { products };
  }

  const productIndex = products.indexOf(
    products.find(({ id }) => id === productId)
  );

  products = products.map((product, index) => {
    if (index == productIndex && (quantity - 1) > 0)
      return { id: product.id, quantity: product.quantity - 1 }

    return product;
  });

  localStorage.setItem('cart', JSON.stringify({ products }));
  return { products };
}

const restore = payload => {
  return payload;
}

const destroy = () => {
  localStorage.removeItem('cart');
  return { products: [] };
}


export const CartContext = createContext();

export const cartReducer = (state, action) => {
  switch (action.type) {
    case 'PUT':
      return put(state.products, action.payload);

    case 'REMOVE':
      return remove(state.products, action.payload);

    case 'RESTORE':
      return restore(action.payload);

    case 'DESTROY':
      return destroy();

    default:
      return state;
  }
}

export const CartContextProvider = ({ children }) => {
  const [state, dispatch] = useReducer(cartReducer, {
    products: []
  });

  useEffect(() => {
    const cart = JSON.parse(localStorage.getItem('cart'));

    if (cart)
      dispatch({ type: 'RESTORE', payload: cart });
  }, []);

  return (
    <CartContext.Provider value={{ ...state, dispatch }}>
      { children }
    </CartContext.Provider>
  );
}