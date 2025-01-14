// pages/search.tsx
import React from 'react';
import dynamic from 'next/dynamic';

const SearchPage = dynamic(() => import('../Components/SearchRecipes'), { ssr: false });

const Search: React.FC = () => {
    return (
        <div>
            <SearchPage />
        </div>
    );
};

export default Search;
