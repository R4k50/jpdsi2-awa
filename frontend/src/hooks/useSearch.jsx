import { useState } from 'react';

export default function useSearch(path, refetch) {
  const size = 1;
  const [params, setParams] = useState('');
  const [searchParam, setSearchParam] = useState('id');
  const [searchValue, setSearchValue] = useState(null);
  const [order, setOrder] = useState('id');
  const [orderValue, setOrderValue] = useState(true);
  const [currentPage, setCurrentPage] = useState(0);

  const Page = {
    next: (totalPages) => {
      if (currentPage < totalPages) {
        refetch(`${path}?page=${currentPage + 1}${params}&size=${size}`);
        setCurrentPage(prev => prev + 1);
      }
    },
    previous: () => {
      if (currentPage > 0) {
        refetch(`${path}?page=${currentPage - 1}${params}&size=${size}`);
        setCurrentPage(prev => prev - 1);
      }
    },
    current: currentPage
  };

  const Filter = {
    get: searchParam,
    set: ({target}) => {
      setSearchParam(() => target.value);
    },
    setValue: ({target}) => {
      setSearchValue(() => target.value);
    }
  };

  const Order = {
    get: order,
    getValue: orderValue,
    set: ({target}) => {
      setOrderValue(prev => !prev);

      if (order != target.innerHTML)
        setOrderValue(() => true);

      setOrder(() => target.innerHTML);
    }
  };

  const Search = {
    start: () => {
      setParams(
        ((searchParam && searchValue) ? `&search=${searchParam},${searchValue}` : '')
        .concat(order ? `&sort=${order},${orderValue ? 'asc' : 'desc'}` : '')
      );

      refetch(
        `${path}?page=${currentPage}`
        .concat((searchParam && searchValue) ? `&search=${searchParam},${searchValue}` : '')
        .concat(order ? `&sort=${order},${orderValue ? 'asc' : 'desc'}` : '')
        .concat(`&size=${size}`)
      );
    },
    clear: () => {
      setCurrentPage(() => 0);
      setParams("");
      setSearchParam("id");
      setSearchValue("");
      setOrder("id");
      setOrderValue(true);
      refetch(`${path}?page=${currentPage}&size=${size}`);
    }
  };

  return {
    Page,
    Filter,
    Order,
    Search
  }
}