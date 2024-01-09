import './styles/Delivery.scss';
import useFetch from '../../hooks/useFetch';
import useAssignOrder from '../../hooks/useAssignOrder';
import useDelete from '../../hooks/useDelete';
import { Icon } from '@iconify/react';
import { useEffect, useState } from 'react';
import ButtonAction from '../../components/ButtonAction';

const Delivery = () => {
  const {
    data: ordersPage,
    isLoading,
    errors,
    refetch
  } = useFetch('/unassigned-orders');
  const [orders, setOrders] = useState(ordersPage?.content);

  const {
    data: assignedOrdersPage,
    isLodading: assignedIsLoading,
    errors: assignedErrors,
    refetch: assignedRefetch
  } = useFetch('/assigned-orders');
  const [assignedOrders, setAssignedOrders] = useState(assignedOrdersPage?.content);

  const { assign } = useAssignOrder();
  const { deleteById } = useDelete('/assigned-order');

  useEffect(() => {
    setOrders(ordersPage?.content);
    setAssignedOrders(assignedOrdersPage?.content);
  }, [ordersPage, assignedOrdersPage]);

  useEffect(() => {
    const interval = setInterval(() => {
      refetch('/unassigned-orders');
      assignedRefetch('/assigned-orders');
    }, 10000);

    return () => clearInterval(interval);
  }, []);

  const handleAssign = async (id) => {
    await assign(id);
    refetch('/unassigned-orders');
    assignedRefetch('/assigned-orders');
  }

  const handleClose = async (id) => {
    await deleteById(id, 'Confirm closing this order');
    refetch('/unassigned-orders');
    assignedRefetch('/assigned-orders');
  }


  return (
    <div className="Delivery">
      <h2>DELIVERY</h2>
      <div className="container">
        <section className="assigned">
          <h3>assigned orders</h3>
          {assignedIsLoading && <Icon icon='eos-icons:bubble-loading' />}
          {assignedOrders?.map(order => (
            <div className='order' key={`assigned${order.id}`}>
              <div className='header'>
                <p className='address'>{order.address}</p>
              </div>
              <p className='city'><span>{order.city},</span> {order.postal_code}</p>
              <p className='id'>Order id: {order.id}</p>
              <ButtonAction onClick={() => handleClose(order.id)}>close order</ButtonAction>
            </div>
          ))}
          {assignedOrders?.length == 0 && !assignedIsLoading && (
            <Icon icon='system-uicons:box-open'/>
          )}
          {assignedErrors && !assignedOrders && <div className="errors">{assignedErrors}</div>}
        </section>
        <section className="orders">
          <h3>unassigned orders</h3>
          {isLoading && <Icon icon='eos-icons:bubble-loading' />}
          {orders?.map(order => (
            <div className='order' key={order.id}>
              <div className='header'>
                <p className='address'>{order.address}</p>
              </div>
              <p className='city'><span>{order.city},</span> {order.postal_code}</p>
              <p className='id'>Order id: {order.id}</p>
              <ButtonAction onClick={() => handleAssign(order.id)}>assign</ButtonAction>
            </div>
          ))}
          {orders?.length == 0 && !isLoading && (
            <Icon icon='system-uicons:box-open'/>
          )}
          {errors && !orders && <div className="errors">{errors}</div>}
        </section>
      </div>
    </div>
  );
}

export default Delivery;