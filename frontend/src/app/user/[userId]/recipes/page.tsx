'use client'; // Add this line

import React from 'react';
import MyRecipes from '@/app/Components/MyRecipes';
import { useParams } from 'next/navigation';

const MyRecipesPage: React.FC = () => {
    const params = useParams();
    const userId = params?.userId;

    if (!userId) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <MyRecipes />
        </div>
    );
};

export default MyRecipesPage;
