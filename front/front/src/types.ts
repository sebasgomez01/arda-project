export type PostResponse = {
    textContent: string,
    title: string,
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