import { Product } from "./model";

export type ProductProps = {
  product: Product; // Ensure this matches your Product type definition
};

export type ProductFilterProps = {
  setProducts: React.Dispatch<React.SetStateAction<Product[]>>;
};
