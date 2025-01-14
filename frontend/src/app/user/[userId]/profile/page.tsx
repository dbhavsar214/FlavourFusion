'use client';
import React from 'react';
import UserProfile from '../../../Components/UserProfile';
import { useParams } from 'next/navigation';

const UserProfilePage: React.FC = () => {
    const params = useParams();
    const userId = params.userId ? Number(params.userId) : null;

    if (!userId) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <UserProfile userId={userId} />
        </div>
    );
};

export default UserProfilePage;
