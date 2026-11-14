"""Project icon = original Saphyra logo + a 'SAPHYRA REBORN' banner along the bottom. Output 400x400 PNG."""
import pathlib
from PIL import Image, ImageDraw, ImageFont, ImageFilter

here = pathlib.Path(__file__).parent
SIZE = 400
base = Image.open(here / 'original_logo.png').convert('RGBA').resize((SIZE, SIZE), Image.NEAREST)

banner_h = 84
overlay = Image.new('RGBA', (SIZE, SIZE), (0, 0, 0, 0))
d = ImageDraw.Draw(overlay)
# dark gradient band so the text reads on any part of the art
for y in range(SIZE - banner_h - 30, SIZE):
    t = min(1.0, (y - (SIZE - banner_h - 30)) / 30.0)
    d.line([(0, y), (SIZE, y)], fill=(12, 8, 20, int(215 * t)))
img = Image.alpha_composite(base, overlay)

font = ImageFont.truetype(r'C:\Windows\Fonts\georgiab.ttf', 44)
text = 'SAPHYRA REBORN'
draw = ImageDraw.Draw(img)
while draw.textlength(text, font=font) > SIZE - 24:
    font = ImageFont.truetype(r'C:\Windows\Fonts\georgiab.ttf', font.size - 2)
w = draw.textlength(text, font=font)
x = (SIZE - w) / 2
bbox = draw.textbbox((0, 0), text, font=font)
y = SIZE - banner_h / 2 - (bbox[3] - bbox[1]) / 2 - bbox[1] - 4

# soft cyan glow behind crisp text
glow = Image.new('RGBA', (SIZE, SIZE), (0, 0, 0, 0))
ImageDraw.Draw(glow).text((x, y), text, font=font, fill=(90, 210, 255, 255))
glow = glow.filter(ImageFilter.GaussianBlur(5))
img = Image.alpha_composite(img, glow)
draw = ImageDraw.Draw(img)
draw.text((x, y), text, font=font, fill=(235, 248, 255, 255), stroke_width=2, stroke_fill=(20, 40, 70, 255))
# thin accent line above the title
draw.line([(SIZE * 0.2, y + bbox[1] - 10), (SIZE * 0.8, y + bbox[1] - 10)], fill=(120, 220, 255, 200), width=2)

img.save(here / 'cf-project-icon.png', optimize=True)
print('saved cf-project-icon.png', img.size)
