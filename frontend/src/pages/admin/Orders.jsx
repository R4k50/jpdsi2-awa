import './styles/Orders.scss';
import ButtonAction from '../../components/ButtonAction';
import Filter from '../../components/Filter';

const Orders = () => {
  return (
    <div className="AdminOrders">
      <ButtonAction to='/admin/dashboard'>return to dashboard</ButtonAction>
      <Filter
        model='order'
        modelPlural='orders'
        disabledColumns={['products']}
      />
    </div>
  );
}

export default Orders;