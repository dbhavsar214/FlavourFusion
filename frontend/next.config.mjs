/** @type {import('next').NextConfig} */
const nextConfig = {
    images: {
        remotePatterns: [{
            protocol: 'https',
            hostname: 'images.unsplash.com',
        }],
    },
    async rewrites() {
        return [
            {
                source: '/api/:path*',
                destination: 'http://csci5308-vm2.research.cs.dal.ca:8080/:path*', // Adjust the backend URL as needed
            },
        ];
    },
};

export default nextConfig;