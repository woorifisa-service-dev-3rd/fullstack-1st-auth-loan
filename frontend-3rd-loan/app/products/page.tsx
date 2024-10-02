"use client";

import ProductFilter from "@/components/ProductFilter";
import ProductItem from "@/components/ProductItem";
import { Product } from "@/types/model";
import React, { useEffect, useState } from "react";

const ProductList = () => {
  const [products, setProducts] = useState<Product[]>([]);
  //const [filteredProducts, setFilteredProducts] = useState<Product[]>(products);

  const fetchProducts = async () => {
    const url = process.env.NEXT_PUBLIC_SERVER_API_URL;
    const response = await fetch(`${url}/loan-products`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    });
    const data = await response.json();
    setProducts(data);
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  return (
    <div className="w-1/2">
      <h1 className="text-3xl font-bold text-center">대출 상품</h1>
      <ProductFilter setProducts={setProducts}></ProductFilter>
      <ul>
        {products.length == 0 ? (
          <div className="text-center text-xl m-5">로딩중...</div>
        ) : (
          products.map((product) => <ProductItem key={product.id} product={product}></ProductItem>)
        )}
      </ul>
    </div>
  );
};

export default ProductList;
