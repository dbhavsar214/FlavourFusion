'use client';
import React, { useEffect, useState } from 'react';
import { UserProfileInfo, AuthenticatedUserProfileInfo } from '@/outputTypes/types';
import { fetchUserProfile, fetchAuthenticatedUserDetails, followUser, unfollowUser } from '@/services/userService';
import UserRecipes from './UserRecipies';
import { useRouter } from 'next/navigation';

const UserProfile: React.FC<{ userId: number }> = ({ userId }) => {
    const [userData, setUserData] = useState<UserProfileInfo | null>(null);
    const [authenticatedUserData, setAuthenticatedUserData] = useState<AuthenticatedUserProfileInfo | null>(null);
    const [loading, setLoading] = useState(true);
    const [isFollowing, setIsFollowing] = useState(false);
    const router = useRouter();

    const checkFollowingStatus = (authData: AuthenticatedUserProfileInfo) => {
        if (authData && authData.followedUserIds.includes(userId)) {
            setIsFollowing(true);
        } else {
            setIsFollowing(false);
        }
    };

    useEffect(() => {
        const fetchData = async () => {
            const data = await fetchUserProfile(userId);
            setUserData(data);

            const authData = await fetchAuthenticatedUserDetails('profile') as AuthenticatedUserProfileInfo;
            setAuthenticatedUserData(authData);
            checkFollowingStatus(authData);

            setLoading(false);
        };

        fetchData();
    }, [userId]);

    const handleFollow = async () => {
        if (authenticatedUserData) {
            try {
                const updatedFollowerIds = await followUser(userId, authenticatedUserData.id);
                setIsFollowing(true);
                // Update the followerIds
                setAuthenticatedUserData((prevData) => {
                    if (prevData) {
                        return {
                            ...prevData,
                            followedUserIds: updatedFollowerIds,
                        };
                    }
                    return prevData;
                });
            } catch (error) {
                console.error('Error following user:', error);
            }
        }
    };

    const handleUnfollow = async () => {
        if (authenticatedUserData) {
            try {
                const updatedFollowerIds = await unfollowUser(userId, authenticatedUserData.id);
                setIsFollowing(false);
                // Update the followerIds
                setAuthenticatedUserData((prevData) => {
                    if (prevData) {
                        return {
                            ...prevData,
                            followedUserIds: updatedFollowerIds,
                        };
                    }
                    return prevData;
                });
            } catch (error) {
                console.error('Error unfollowing user:', error);
            }
        }
    };

    const handleEditProfile = () => {
        router.push(`/user/${userId}/update`);
    };

    if (loading) {
        return <div>Loading...</div>;
    }

    if (!userData) {
        return <div>Error loading user data</div>;
    }

    const imageURL = userData.imageURL || '/images/DefaultProfile.jpg';

    return (
        <div>
            <div className="max-w-full mx-auto p-8 flex items-center space-x-8">
                <div className="w-2/3">
                    <h2 className="text-4xl font-bold mb-4">{userData.name}</h2>
                    <p className="text-gray-500 text-lg mb-4">{userData.followers} followers</p>
                    <p className="text-gray-700 text-lg mb-8">{userData.description}</p>
                    {authenticatedUserData && authenticatedUserData.id === userId ? (
                        <button
                            className="px-6 py-3 rounded-lg text-white font-bold bg-teal-600 hover:bg-teal-700"
                            onClick={handleEditProfile}
                        >
                            Edit Profile
                        </button>
                    ) : (
                        authenticatedUserData && (
                            <button
                                className={`px-6 py-3 rounded-lg text-white font-bold ${isFollowing ? 'bg-red-600 hover:bg-red-700' : 'bg-teal-600 hover:bg-teal-700'}`}
                                onClick={isFollowing ? handleUnfollow : handleFollow}
                            >
                                {isFollowing ? 'Unfollow' : 'Follow'}
                            </button>
                        )
                    )}
                </div>
                <div className="w-1/3">
                    <img className="w-full rounded-lg" src={imageURL} alt={`${userData.name}'s avatar`} />
                </div>
            </div>
            <UserRecipes recipes={userData.recipes} />
        </div>
    );
};

export default UserProfile;
