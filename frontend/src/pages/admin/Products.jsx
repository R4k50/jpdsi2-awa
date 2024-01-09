import './styles/Products.scss';
import useForm from '../../hooks/useForm';
import usePost from '../../hooks/usePost';
import Form from '../../components/Form';
import Input from '../../components/Input';
import Filter from '../../components/Filter';
import { FileUploader } from 'react-drag-drop-files';
import { useState } from 'react';

const Products = () => {
  const { post, isLoading: isLoadingNew, errors: errorsNew } = usePost('/product', 'multipart/form-data');
  const { data: product, handleChange } = useForm();
  const [image, setImage] = useState(null);
  const [isSubmitted, setIsSubmitted] = useState(false);

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

    await post(newProduct);
    setIsSubmitted(prev => !prev);
  }

  const handleUpload = file => {
    setImage(file);
  };

  const imageTypes = ["JPG", "PNG"];

  return (
    <div className="Products">
      <Form
        title={'CREATE A NEW PRODUCT'}
        accept="create"
        deny="return to dashboard"
        denyredirect='/admin/dashboard'
        errors={errorsNew}
        isloading={isLoadingNew ? 1 : 0}
        onSubmit={handleSubmit}
      >
        <Input
          label='name'
          name='name'
          onChange={handleChange}
        />
        <Input
          label='ingredients'
          name='ingredients'
          onChange={handleChange}
        />
        <Input
          label='price'
          name='price'
          onChange={handleChange}
        />
        <FileUploader
          handleChange={handleUpload}
          name="image"
          types={imageTypes}
        />
      </Form>
      <Filter
        model='product'
        modelPlural='products'
        refreshing={isSubmitted}
      />
    </div>
  );
}

export default Products;