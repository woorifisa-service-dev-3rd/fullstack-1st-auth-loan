import { ProductFilterProps } from "@/types/props";
import React from "react";

const ProductFilter: React.FC<ProductFilterProps> = ({ setProducts }) => {
  const filters = [
    { id: 1, name: "검색 조건", bgcolor: "bg-blue-500" },
    {
      id: 2,
      name: "금리순",
      bgcolor: "bg-gray-400",
      clickHandler: () => {
        fetchProducts("interestRate");
      },
    },
    {
      id: 3,
      name: "한도순",
      bgcolor: "bg-gray-400",
      clickHandler: () => {
        fetchProducts("maxLimit");
      },
    },
    {
      id: 4,
      name: "신용점수순",
      bgcolor: "bg-gray-400",
      clickHandler: () => {
        fetchProducts("requiredCreditScore");
      },
    },
    { id: 5, name: "초기화", bgcolor: "bg-red-500" },
  ];

  const fetchProducts = async (condition: string) => {
    const url = process.env.NEXT_PUBLIC_SERVER_API_URL;
    const response = await fetch(`${url}/loan-products?filterName=` + condition, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    });
    const data = await response.json();
    setProducts(data);
  };

  return (
    <div className="border-4 border-blue-500 rounded-md mt-5 p-3 flex justify-center">
      {filters.map((filter) => (
        <button
          key={filter.id}
          className={`border rounded-md mr-5 text-black p-1 ${filter.bgcolor} hover:ring ring-black`}
          onClick={filter.clickHandler}
        >
          {filter.name}
        </button>
      ))}
    </div>
  );
};

export default ProductFilter;
