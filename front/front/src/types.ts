export type PostResponse = {
    textContent: string,
    title: string,
    imagePath: string,
    _links: {
        dislikes: {
            href: string
        },
        likes: {
            href: string
        },
        post: {
            href: string
        },
        reposts: {
            href: string
        },
        self: {
            href: string
        },
        user: {
            href: string 
        },
   };
}

export type UserResponse = {
    name: string,
    username: string,
    _links: {
        dislikedPost: {
            href: string
        },
        followers: {
            href: string
        },
        likedPost: {
            href: string
        },
        notifications: {
            href: string
        },
        posts: {
            href: string
        },
        reposts: {
            href: string
        },
        self: {
            href: string
        },
        user: {
            href: string
        }
    }
}