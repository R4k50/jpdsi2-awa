import './styles/Dashboard.scss';
import ButtonAction from '../../components/ButtonAction';

const Dashboard = () => {
  return (
    <div className="Dashboard">
    <h1>DASHBOARD</h1>
      <nav>
        <ButtonAction to='/admin/products'>Products</ButtonAction>
        <ButtonAction to='/admin/orders'>Orders</ButtonAction>
        <ButtonAction to='/admin/users'>Users</ButtonAction>
      </nav>
    </div>
  );
}

export default Dashboard;