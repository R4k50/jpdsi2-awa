import './styles/Orders.scss';
import { useTrail, useTransition, animated } from 'react-spring';
import useFetch from '../hooks/useFetch';
import { Icon } from '@iconify/react';
import formatDistanceToNow from 'date-fns/formatDistanceToNow'
import { useEffect } from 'react';

const Orders = () => {
  const { data: orders, isLoading, errors, refetch } = useFetch('/my-orders');

  const timeToNow = date => formatDistanceToNow(new Date(date), { addSuffix: true });

  useEffect(() => {
    const interval = setInterval(() => {
      refetch();
    }, 60000);

    return () => clearInterval(interval);
  }, [refetch]);

  const trails = useTrail((orders ? orders.length : 0), {
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

  const transition = useTransition(orders?.length == 0, {
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

  return (
    <div className="Orders">
      <h2>ORDERS</h2>
      <div className="container">
        {isLoading && <Icon icon='eos-icons:bubble-loading' />}
        {orders && trails.map((props, i) => (
          <animated.div className='order' style={props} key={i}>
            <div className='header'>
              <p className='address'>{orders[i].address}</p>
              {/* <p className='time'>{timeToNow(orders[i].created_at)}</p> */}
            </div>
            <p className='city'><span>{orders[i].city},</span> {orders[i].postalCode}</p>
            <p style={{
              color: orders[i].deliveryManId ? '#699BF7' : '#E56464'
            }}>
              {orders[i].deliveryManId ? 'ON THE WAY' : 'PREPARING'}
            </p>
            <p className='id'>Order id: {orders[i].id}</p>
          </animated.div>
        ))}
        {transition((style, item) => (
          item && <animated.div className='empty' style={style}>
            <Icon icon='system-uicons:box-open'/>
            <h3>You don't have any orders...</h3>
          </animated.div>
        ))}
        {errors && !orders && <div className="errors">{errors}</div>}
      </div>
    </div>
  );
}

export default Orders;