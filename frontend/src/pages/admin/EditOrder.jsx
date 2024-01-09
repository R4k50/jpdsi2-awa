import './styles/EditOrder.scss';
import { useParams } from 'react-router-dom'
import Form from '../../components/Form';
import Input from '../../components/Input';
import useForm from '../../hooks/useForm';
import useFetch from '../../hooks/useFetch';
import usePatch from '../../hooks/usePatch';

const EditOrder = () => {
  const { id } = useParams();
  const { data: oldOrder, isLoading, errors } = useFetch(`/order/${id}`);
  const { data: order, handleChange } = useForm();
  const { patchById, errors: patchErrors } = usePatch('/order');

  const handleSubmit = async (e) => {
    e.preventDefault();
    await patchById(order, id, '/admin/orders');
  }

  return (
    <div className="EditOrder">
      <Form
        title={`UPDATE ORDER ${id}`}
        accept="update"
        deny='back to orders'
        denyredirect="/admin/orders"
        onSubmit={handleSubmit}
        errors={patchErrors}
        isloading={isLoading ? 1 : 0}
      >
        <Input
          label='address'
          name='address'
          value={oldOrder?.address}
          onChange={handleChange}
        />
        <Input
          label='city'
          name='city'
          value={oldOrder?.city}
          onChange={handleChange}
        />
        <Input
          label='postal code'
          name='postal_code'
          value={oldOrder?.postal_code}
          onChange={handleChange}
        />
        <Input
          label='notes'
          name='notes'
          value={oldOrder?.notes ?? ''}
          onChange={handleChange}
        />
      </Form>
    </div>
  );
}

export default EditOrder;