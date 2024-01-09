import './styles/Menu.scss';
import Product from '../components/Product';
import { useTrail, animated } from 'react-spring';
import useFetch from '../hooks/useFetch';
import { Icon } from '@iconify/react';

const Menu = () => {
  const { data, isLoading, errors } = useFetch('/products');
  const products = data?.content;

  const trails = useTrail((products ? products.length : 0), {
    from: {
      opacity: 0,
      scale: .9,
      y: 50
    },
    to: {
      opacity: 1,
      scale: 1,
      y: 0
    },
    delay: 100
  });


  return (
    <div className="Menu">
      <h2>MENU</h2>
      <div className="container">
        {isLoading && <Icon icon='eos-icons:bubble-loading' />}
        {products && trails.map((props, i) => (
          <animated.div style={props} key={i}>
            <Product product={products[i]} />
          </animated.div>
        ))}
        {errors && <div className="errors">{errors}</div>}
      </div>
    </div>
  );
}

export default Menu;