"use client";

import React, { useEffect, useState } from "react";
import { Product } from "@/types/model"; // 타입 가져오기

type ProductDetailProps = {
  params: {
    productId: string;
  };
};

const ProductDetail: React.FC<ProductDetailProps> = ({ params }) => {
  const [product, setProduct] = useState<Product | null>(null);

  const fetchProduct = async () => {
    const url = process.env.NEXT_PUBLIC_SERVER_API_URL;
    const response = await fetch(`${url}/loan-products/` + params.productId, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    });
    const data = await response.json();
    setProduct(data);
  };

  useEffect(() => {
    fetchProduct();
  }, []);

  return (
    <div className="w-1/2 text-center bg-white border-4 border-blue-500 rounded-md m-5 p-5">
      {product === null ? (
        <div>로딩 중...</div>
      ) : (
        <>
          <h1 className="text-3xl font-bold mb-5">{product.loanProductsType}</h1>
          <dl>
            <div className="mb-4">
              <dt className="font-bold">최저 금리</dt>
              <dd>{product.interestRate}%</dd>
            </div>
            <div className="mb-4">
              <dt className="font-bold">최대 한도</dt>
              <dd>{product.maxLimit.toLocaleString()}원</dd>
            </div>
            <div className="mb-4">
              <dt className="font-bold">상환 기간</dt>
              <dd>{product.repaymentPeriod}개월</dd>
            </div>
            <div className="mb-4">
              <dt className="font-bold">신청 방법</dt>
              <dd>{product.applicationMethod}</dd>
            </div>
            <div className="mb-4">
              <dt className="font-bold">대출 상품 특징</dt>
              <dd>{product.loanProductsFeature}</dd>
            </div>
            <div className="mb-4">
              <dt className="font-bold">필요한 신용 점수</dt>
              <dd>{product.requiredCreditScore}점</dd>
            </div>
            <div className="mb-4">
              <dt className="font-bold">제공자</dt>
              <dd>{product.provider}</dd>
            </div>
          </dl>
          <button className="bg-blue-600 border rounded-md p-3 text-white">대출하기</button>
        </>
      )}
    </div>
  );
};

export default ProductDetail;
