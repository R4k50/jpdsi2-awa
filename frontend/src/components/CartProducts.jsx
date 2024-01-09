import { useEffect, useState } from 'react';
import { useTransition, animated } from 'react-spring';
import { Icon } from '@iconify/react';
import useCartContext from '../hooks/useCartContext';
import useFetch from '../hooks/useFetch';
import Product from './Product';

export default function CartProducts() {
  const { products: cartProducts } = useCartContext();
  const { data, isLoading, errors } = useFetch('/products');
  const fetchedProducts = data?.content;
  const [products, setProducts] = useState([]);

  useEffect(() => {
    setProducts(() => (
      fetchedProducts && !isLoading && cartProducts && cartProducts.map(({ id }) => {
        return fetchedProducts.filter(
          fetchedProduct => (fetchedProduct.id === id)
        );
      }).flat()
    ));
  }, [cartProducts, fetchedProducts]);

  const emptyTransition = useTransition(products?.length == 0, {
    from: {
      opacity: 0,
      scale: .8,
      y: 50
    },
    enter: {
      opacity: 1,
      scale: 1,
      y: 0
    },
    delay: 200
  });

  const productTransition = useTransition(products, {
    from: {
      opacity: 0,
      scale: .8,
      x: 0,
      y: 50
    },
    enter: {
      opacity: 1,
      scale: 1,
      x: 0,
      y: 0
    },
    leave: {
      opacity: 0,
      scale: 1,
      x: -50,
      y: 0
    },
    trail: 100
  });

  return <>
    {isLoading && <Icon icon='eos-icons:bubble-loading' />}
    {emptyTransition((style, item) => (
      item && <animated.div className='empty' style={style}>
        <Icon icon='system-uicons:box-open'/>
        <h3>You don't have any pizza in your cart :(</h3>
      </animated.div>
    ))}
    {productTransition((style, product) => (
      product && <Product product={product} style={{...style, height: 'initial'}} key={product.id} />
    ))}
    {errors && <div className="errors">{errors}</div>}
  </>
}