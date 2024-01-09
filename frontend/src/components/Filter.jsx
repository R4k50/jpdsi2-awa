import './styles/Filter.scss';
import { Icon } from '@iconify/react';
import { useEffect, useMemo, useState } from 'react';
import useFetch from '../hooks/useFetch';
import useDelete from '../hooks/useDelete';
import Input from './Input';
import ButtonAction from './ButtonAction';
import Errors from './Errors';

const Filter = ({model, modelPlural, disabledColumns, refreshing, size = 1}) => {
  const { data, isLoading, errors: fetchErrors, refetch } = useFetch(`/${modelPlural}?page=0&size=${size}`);
  const [page, setPage] = useState(data);
  const [content, setProducts] = useState(data?.content);
  const { deleteById, errors: deleteErrors } = useDelete(`/${model}`);

  const [searchParam, setSearchParam] = useState('id');
  const [searchValue, setSearchValue] = useState(null);
  const [order, setOrder] = useState('id');
  const [orderValue, setOrderValue] = useState(true);

  const errors = useMemo(() => {
    if (fetchErrors)
      return fetchErrors;

    return deleteErrors;
  }, [fetchErrors, deleteErrors]);


  useEffect(() => {
    setTimeout(() => {
      toPage(0);
    }, 100);
  }, [refreshing]);

  useEffect(() => {
    setPage(prev => data);
    setProducts(prev => data?.content);
  }, [data]);

  useEffect(() => {
    toPage(0);
  }, [order, orderValue]);

  const toPage = (number = 0) => {
    refetch(
      `/${modelPlural}?page=${number}`
      .concat((searchParam && searchValue) ? `&search=${searchParam},${searchValue}` : '')
      .concat(order ? `&sort=${order}${orderValue ? ',asc' : ',desc'}` : '')
      .concat(`&size=${size}`)
    );
  }

  const normalizeCamelCase = camelCase => (
    camelCase.replace(/([a-z])([A-Z])/g, '$1 $2').toLowerCase()
  );


  const handleDelete = async (id) => {
    if (await deleteById(id)) {
      toPage(0);
    }
  }

  const handleSearch = () => {
    toPage(page?.number);
  }

  const handleClear = () => {
    setSearchParam("id");
    setSearchValue("");
    setOrder("id");
    setOrderValue(true);

    refetch(`/${modelPlural}?page=0`.concat(`&size=${size}`));
  }

  const handleNext = () => {
    if (page?.totalPages > 1 && page?.last == false) {
      toPage(page?.number + 1);
    }
  }

  const handlePrev = () => {
    if (page?.totalPages > 1 && page?.first == false) {
      toPage(page?.number - 1);
    }
  }

  const handleOrder = (columnName) => {
    setOrderValue(prev => !prev);

    if (order != columnName)
      setOrderValue(() => true);

    setOrder(() => columnName);
  }

  const handleSearchParam = ({target}) => {
    setSearchParam(() => target.value);
  }

  const handleSearchValue = ({target}) => {
    setSearchValue(() => target.value);
  }

  return (
    <div className='Filter'>
      <h2>{modelPlural}</h2>
      <div className="search">
        <select onChange={handleSearchParam}>
          {content?.length > 0 && Object.keys(content[0])?.map(name => (
            <option value={name} key={name}>{name}</option>
          ))}
        </select>
        <Input
          label='filter'
          name={searchParam}
          value={searchValue}
          onChange={handleSearchValue}
        />
        <ButtonAction onClick={handleSearch}>Search</ButtonAction>
        <ButtonAction onClick={handleClear}>Clear</ButtonAction>
      </div>
      {content?.length > 0 && <div className='paging'>
        {(page?.totalPages > 1 && data?.first == false) && <ButtonAction onClick={handlePrev}>previous</ButtonAction>}
        <span>{data?.number + 1} / {data?.totalPages}</span>
        {(page?.totalPages > 1 && data?.last == false) && <ButtonAction onClick={handleNext}>next</ButtonAction>}
      </div>}
      {isLoading && <div>LOADING <Icon icon='eos-icons:bubble-loading' /></div>}
      {content?.length > 0 && <table>
        <thead>
          <tr>
            {content?.length > 0 && Object.keys(content[0])
            ?.filter(columnName => !disabledColumns?.includes(columnName))
            .map(columnName => (
              <th onClick={() => handleOrder(columnName)} key={columnName}>
                {normalizeCamelCase(columnName)}
                {(order != columnName) && <Icon icon='raphael:arrowup' style={{color: '#fff'}}/>}
                {(order == columnName) && (orderValue == true) && <Icon icon='raphael:arrowup' />}
                {(order == columnName) && (orderValue == false) && <Icon icon='raphael:arrowdown' />}
              </th>
            ))}
          </tr>
        </thead>
        <tbody>
          {content?.map(item => (
            <tr key={item.id}>
              {content && Object.keys(content[0])
              ?.filter(columnName => !disabledColumns?.includes(columnName))
              .map(columnName => (
                <td key={columnName}>
                  {(item[columnName] == null) ? "none" : ""}
                  {Array.isArray(item[columnName]) && item[columnName]
                  ?.filter(element => (
                    typeof element != 'object'
                  ))
                  .join(', ')}
                  {(typeof item[columnName] == 'object') ? '' : item[columnName]}
                </td>
              ))}
              <td><ButtonAction appearance='alt' to={`/admin/edit-${model}/${item.id}`}>EDIT</ButtonAction></td>
              <td><ButtonAction onClick={() => handleDelete(item.id)}>DELETE</ButtonAction></td>
            </tr>
          ))}
        </tbody>
      </table>}
      {content?.length == 0 && !isLoading && <div>No content</div>}
      <Errors errors={errors} />
      {content?.length > 5 && <div className='paging'>
        {(page?.totalPages > 1 && data?.first == false) && <ButtonAction onClick={handlePrev}>previous</ButtonAction>}
        <span>{data?.number + 1}/{data?.totalPages}</span>
        {(page?.totalPages > 1 && data?.last == false) && <ButtonAction onClick={handleNext}>next</ButtonAction>}
      </div>}
    </div>
  );
}

export default Filter;