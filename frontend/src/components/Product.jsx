import './styles/Product.scss';
import { forwardRef } from 'react';
import { animated } from 'react-spring';
import { Icon } from '@iconify/react';
import useAuthContext from '../hooks/useAuthContext';
import useAddToCart from '../hooks/useAddToCart';
import useRemoveFromCart from '../hooks/useRemoveFromCart';
import ButtonAction from '../components/ButtonAction';
import useCartContext from '../hooks/useCartContext';

const Product = forwardRef((props, ref) => {
  const { product } = props;
  const { userData } = useAuthContext();
  const { products: cartProducts } = useCartContext();
  const { addToCart } = useAddToCart();
  const { removeFromCart } = useRemoveFromCart();

  const handleAdd = id => {
    addToCart(id);
  }

  const handleRemove = id => {
    removeFromCart(id);
  }

  return (
    <animated.div {...props} className={`${props.className || ''} Product`} ref={ref}>
      <img src={`${import.meta.env.VITE_API_IMAGE_URL}${product.img}`} alt={product.name} />
      <div className="content">
        <div className="main">
          <h3>{product.name}</h3>
          <span>${product.price.toFixed(2)}</span>
        </div>
        <div className="container">
          <p>{product.ingredients}</p>
          {cartProducts?.filter(({ id }) => (id === product.id))
            .map(({ id, quantity }) => (
              <span key={id}><Icon icon='ic:outline-shopping-bag'/>{quantity}</span>
            ))
          }
        </div>
      </div>
      {userData && <ButtonAction onClick={() => handleAdd(product.id)}>add to cart</ButtonAction>}
      {cartProducts?.filter(({ id }) => (id === product.id))
        .map(() => (
          <ButtonAction
            onClick={() => handleRemove(product.id)}
            appearance='alt'
            key={product.id}
          >
            remove
          </ButtonAction>
        ))
      }
    </animated.div>
  )
});

export default Product;