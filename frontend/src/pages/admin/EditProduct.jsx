import './styles/EditProduct.scss';
import { useParams } from 'react-router-dom';
import { FileUploader } from 'react-drag-drop-files';
import { useState } from 'react';
import Form from '../../components/Form';
import Input from '../../components/Input';
import useForm from '../../hooks/useForm';
import useFetch from '../../hooks/useFetch';
import usePatch from '../../hooks/usePatch';

const EditProduct = () => {
  const { id } = useParams();
  const { data: oldProduct, isLoading, errors } = useFetch(`/product/${id}`);
  const { data: product, handleChange } = useForm();
  const { patchById, errors: patchErrors } = usePatch('/product', 'multipart/form-data');
  const [image, setImage] = useState(null);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const newProduct = new FormData();

    const json = JSON.stringify(product);
    const blob = new Blob([json], {
      type: 'application/json'
    });

    newProduct.append("product", blob);

    if (image) {
      newProduct.append("image", image, image?.name);
    }

    patchById(newProduct, id, '/admin/products');
  }

  const handleUpload = file => {
    setImage(file);
  };

  const imageTypes = ["JPG", "PNG"];

  return (
    <div className="EditProduct">
      <Form
        title={`UPDATE PRODUCT ${id}`}
        accept="update"
        deny='back to products'
        denyredirect="/admin/products"
        onSubmit={handleSubmit}
        errors={patchErrors}
        isloading={isLoading ? 1 : 0}
      >
        <Input
          label='name'
          name='name'
          value={oldProduct?.name}
          onChange={handleChange}
        />
        <Input
          label='ingredients'
          name='ingredients'
          value={oldProduct?.ingredients}
          onChange={handleChange}
        />
        <Input
          label='price'
          name='price'
          value={oldProduct?.price}
          onChange={handleChange}
          />
        {!image && <img src={`${import.meta.env.VITE_API_IMAGE_URL}${oldProduct?.img}`} width='150px'/>}
        {image && <p>New image selected</p>}
        <FileUploader
          handleChange={handleUpload}
          name="image"
          types={imageTypes}
        />
      </Form>
    </div>
  );
}

export default EditProduct;