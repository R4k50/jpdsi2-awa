import { BrowserRouter, Routes, Route } from 'react-router-dom';
import ScrollToTop from "./components/ScrollToTop";
import ProtectedRoute from './components/wrappers/ProtectedRoute';
import ProtectedUnverifiedRoute from './components/wrappers/ProtectedUnverifiedRoute';
import DeliveryRoute from './components/wrappers/DeliveryRoute';
import AdminRoute from './components/wrappers/AdminRoute';

import Navbar from './components/Navbar';
import Footer from './components/Footer';
import Home from './pages/Home';
import Login from './pages/Login';
import Register from './pages/Register';
import Menu from './pages/Menu';
import Cart from './pages/Cart';
import Orders from './pages/Orders';
import Delivery from './pages/delivery/Delivery';
import Dashboard from './pages/admin/Dashboard';
import Products from './pages/admin/Products';
import AllOrders from './pages/admin/Orders';
import Users from './pages/admin/Users';
import EditProduct from './pages/admin/EditProduct';
import EditOrder from './pages/admin/EditOrder';
import EditUser from './pages/admin/EditUser';


const View = () => {
  return (
    <BrowserRouter>
      <ScrollToTop />
      <Navbar />
      <main>
        <Routes>
          <Route
            path="/"
            element={<Home />}
          />
          <Route
            path="/login"
            element={
              <ProtectedUnverifiedRoute>
                <Login />
              </ProtectedUnverifiedRoute>
            }
          />
          <Route
            path="/register"
            element={
              <ProtectedUnverifiedRoute>
                <Register />
              </ProtectedUnverifiedRoute>
            }
          />
          <Route
            path="/menu"
            element={<Menu />}
          />
          <Route
            path="/cart"
            element={
              <ProtectedRoute to='/login'>
                <Cart />
              </ProtectedRoute>
            }
          />
          <Route
            path="/orders"
            element={
              <ProtectedRoute to='/login'>
                <Orders />
              </ProtectedRoute>
            }
          />
          <Route
            path="/delivery"
            element={
              <DeliveryRoute>
                <Delivery />
              </DeliveryRoute>
            }
          />
          <Route
            path='/admin/dashboard'
            element={
            <AdminRoute>
              <Dashboard />
            </AdminRoute>
            }
          />
          <Route
            path='/admin/products'
            element={
            <AdminRoute>
              <Products />
            </AdminRoute>
            }
          />
          <Route
            path='/admin/orders'
            element={
            <AdminRoute>
              <AllOrders />
            </AdminRoute>
            }
          />
          <Route
            path='/admin/users'
            element={
            <AdminRoute>
              <Users />
            </AdminRoute>
            }
          />
          <Route
            path='/admin/edit-product/:id'
            element={
            <AdminRoute>
              <EditProduct />
            </AdminRoute>
            }
          />
          <Route
            path='/admin/edit-order/:id'
            element={
            <AdminRoute>
              <EditOrder />
            </AdminRoute>
            }
          />
          <Route
            path='/admin/edit-user/:id'
            element={
            <AdminRoute>
              <EditUser />
            </AdminRoute>
            }
          />
        </Routes>
      </main>
      <Footer />
    </BrowserRouter>
  );
}

export default View;