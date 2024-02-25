-- user table data
insert into user(username,password,nickname,email,user_pic,create_time,update_time) values
('user01','b75705d7e35e7014521a46b532236ec3','','user01@email.com',
'https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/User-avatar.svg/240px-User-avatar.svg.png', now(),now()),
('user02','8bd108c8a01a892d129c52484ef97a0d','','user02@email.com',
'https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/User-avatar.svg/240px-User-avatar.svg.png', now(),now());

-- article category data
insert into category(category_name,category_alias,create_user,create_time,update_time) values
('Health and lifestyle','hth',1,now(),now()),
('Technology and AI','tech','1',now(),now()),
('Education','edu',1,now(),now()),
('Politics','politic',1,now(),now()),
('Arts', 'art', 1, now(),now()),
('Java programming','java',1,now(),now()),
('Python programming','py',1,now(),now()),
('C programming','c',2,now(),now()),
('TV shows','tv',2,now(),now()),
('DIY','diy',2,now(),now());

-- article data
insert into article(title,content,cover_img,state,category_id,create_user,create_time,update_time) values
('AI is Taking Over Developers?','AI technologies, including tools for code generation, automated testing, and bug detection, are aimed at augmenting developers capabilities rather than replacing them entirely. These tools can help developers be more productive, efficient, and focused on high-level tasks. AI opens up new opportunities for developers to innovate and create solutions that were previously not feasible. For example, AI-powered applications in areas such as natural language processing, computer vision, and predictive analytics can create entirely new markets and possibilities for developers.',
'https://www.ucf.edu/wp-content/blogs.dir/20/files/2022/06/computer_eye_for_web.jpg','published',2,1,now(),now()),
('Top 10 Tips for Healthy Eating', 'Eating a healthy diet is essential for maintaining overall health and well-being. Here are the top 10 tips for incorporating healthy eating habits into your lifestyle:',
'https://example.com/cover1.jpg', 'published', 1, 1, now(), now()),
('The Benefits of Regular Exercise', 'Regular exercise offers numerous benefits for both physical and mental health. Discover the many advantages of incorporating exercise into your daily routine:',
 'https://example.com/cover2.jpg', 'draft', 1, 1, now(), now()),
('Introduction to Machine Learning', 'Machine learning is a subset of artificial intelligence that focuses on the development of algorithms and statistical models to perform specific tasks without explicit instructions.',
 'https://example.com/cover4.jpg', 'published', 2, 2, now(), now()),
('The Art of Photography', 'Photography is more than just pointing a camera and clicking a button. Explore the principles and techniques behind capturing stunning images.',
'https://example.com/cover5.jpg', 'draft', 5, 1, now(), now()),
('Mastering Web Development', 'Web development encompasses a range of activities related to developing websites and web applications. Dive into the world of HTML, CSS, JavaScript, and more.',
'https://example.com/cover6.jpg', 'published', 2, 1, now(), now());