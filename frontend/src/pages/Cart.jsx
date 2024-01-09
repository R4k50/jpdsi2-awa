import './styles/Cart.scss';
import { useTransition } from 'react-spring';
import useCartContext from '../hooks/useCartContext';
import useForm from '../hooks/useForm';
import useOrder from '../hooks/useOrder';
import CartProducts from '../components/CartProducts';
import Form from '../components/Form';
import Input from '../components/Input';


const Cart = () => {
  const { data: deliveryDetails, handleChange } = useForm();
  const { order, isLoading, errors } = useOrder();
  const { products } = useCartContext();

  const handleSubmit = e => {
    e.preventDefault();
    order({ ...deliveryDetails, products });
  }

  const transition = useTransition(products?.length > 0, {
    from: {
      opacity: 0,
      x: 100
    },
    enter: {
      opacity: 1,
      x: 0
    },
    leave: {
      opacity: 0,
      x: 100
    },
    delay: 300
  });

  return (
    <div className="Cart">
      <div className="container">
        <h2>SHOPPING CART</h2>
        <div className="products">
          <CartProducts />
        </div>
      </div>
      {transition((style, item) => (
        item && <Form
          title='ORDER'
          accept='checkout'
          style={style}
          onSubmit={handleSubmit}
          isloading={isLoading ? 1 : 0}
          errors={errors}
        >
          <h3>DELIVERY DETAILS</h3>
          <Input onChange={handleChange} name='address' value='' label='address' />
          <Input onChange={handleChange} name='city' value='' label='city' />
          <Input onChange={handleChange} name='postalCode' value='' label='postal code' />
          <Input onChange={handleChange} name='notes' value='' label='notes' />
        </Form>
      ))}
    </div>
  );
}

export default Cart;