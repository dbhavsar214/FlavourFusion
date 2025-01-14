'use client'; // Add this line

import React from 'react';
import UpdateUser from '../../../Components/UpdateUser';
import { useParams } from 'next/navigation';

const UpdateUserPage: React.FC = () => {
    const params = useParams();
    const userId = params?.userId;

    if (!userId) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <UpdateUser userId={userId as string} />
        </div>
    );
};

export default UpdateUserPage;
